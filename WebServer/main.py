#!/usr/bin/env python
# -*- coding: cp949 -*-
#
# Copyright 2007 Google Inc.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
import webapp2
from google.appengine.ext import ndb
from google.appengine.ext import db
from google.appengine.api import users

class GameData(ndb.Model):
    bot_1_id = ndb.IntegerProperty()
    bot_1_ready = ndb.IntegerProperty()
    bot_1_time = ndb.IntegerProperty()
    
    bot_2_id = ndb.IntegerProperty()
    bot_2_ready = ndb.IntegerProperty()    
    bot_2_time = ndb.IntegerProperty()
    
    game_start = ndb.IntegerProperty()    
    findKey = ndb.StringProperty()

class GameDataMgr:
    queryData = 0
    bot_1_id = 0
    bot_2_id = 0
    bot_1_rdy = 0
    bot_2_rdy = 0
    game_start = 0
    bot_1_time = 0
    bot_2_time = 0

    def dataClear(self):
        ndb.delete_multi(GameData.query().fetch(keys_only=True))
    
    def dataReset(self):
        self.dataClear()
        data = GameData(bot_1_id=0, bot_1_ready=0, bot_2_id=0, bot_2_ready=0, game_start=0, bot_1_time=0, bot_2_time=0, findKey='db')
        data.put()

    def loadDB(self):
        query = GameData.query()
        query = GameData.query(GameData.findKey == 'db')
        self.queryData = query.fetch(1)
        self.bot_1_id = self.parseValue('bot_1_id')
        self.bot_2_id = self.parseValue('bot_2_id')
        self.bot_1_rdy = self.parseValue('bot_1_ready')
        self.bot_2_rdy = self.parseValue('bot_2_ready')
        self.game_start = self.parseValue('game_start')
        self.bot_1_time = self.parseValue('bot_1_time')
        self.bot_2_time = self.parseValue('bot_2_time')        

    def parseValue(self, findVariable):
        queryString = str(self.queryData)
        startPos = queryString.find(findVariable)+len(findVariable)+1
        endPos = queryString.find(',', startPos)

        if endPos == -1:
            endPos = queryString.find(')]', startPos)

        strlength = endPos - startPos
        strnum = ""

        for i in range(strlength):
            strnum += queryString[startPos+i]
        
        return int(strnum)
    
    def updateDB(self):
        bot1id = self.bot_1_id
        bot2id = self.bot_2_id
        bot1rdy = self.bot_1_rdy
        bot2rdy = self.bot_2_rdy
        bot1time = self.bot_1_time
        bot2time = self.bot_2_time
        gs = self.game_start

        self.dataClear()

        data = GameData(bot_1_id=0, bot_1_ready=0, bot_2_id=0, bot_2_ready=0, game_start=0, bot_1_time=0, bot_2_time=0, findKey='db')
        data.bot_1_id = int(bot1id)
        data.bot_2_id = int(bot2id)
        data.bot_1_ready = int(bot1rdy)
        data.bot_2_ready = int(bot2rdy)
        data.game_start = int(gs)
        data.bot_1_time = int(bot1time)
        data.bot_2_time = int(bot2time)

        data.put()

class MainHandler(webapp2.RequestHandler):
    def get(self):
        mgr = GameDataMgr()
        mgr.loadDB()

        test = str(self.request.get_all('botid'))
        self.response.write(test)
        self.response.write('<h1>Server status</h1><br><br>')
        self.response.write('bot 1 : ')

        if mgr.bot_1_id == 1:
            output = 'log on'
        else:
            output = 'log off'

        self.response.write(output+'<br><br>')

        self.response.write('bot 2 : ')

        if mgr.bot_2_id == 1:
            output = 'log on'
        else:
            output = 'log off'

        self.response.write(output+'<br><br>')

        self.response.write('bot 1 : ')

        if mgr.bot_1_rdy == 1:
            output = 'ready'
        else:
            output = 'wait'

        self.response.write(output+'<br><br>')
        self.response.write('bot 2 : ')

        if mgr.bot_1_rdy == 1:
            output = 'ready'
        else:
            output = 'wait'

        self.response.write(output+'<br><br>')

        #
        self.response.write('bot 1 : ')
        self.response.write(str(mgr.bot_1_time)+'<br><br>')
        self.response.write('bot 2 : ')
        self.response.write(str(mgr.bot_2_time)+'<br><br>')
        #

        self.response.write('Game Start : ')
        if mgr.game_start == 1:
            output = 'start'
        else:
            output = 'not start'

        self.response.write(output)


class GetBotID(webapp2.RequestHandler):
    def get(self):
        mgr = GameDataMgr()
        mgr.loadDB()

        bot1id = mgr.parseValue('bot_1_id')
        bot2id = mgr.parseValue('bot_2_id')

        if bot1id == 0:
            self.response.write(5)
            mgr.bot_1_id = 1
        elif bot2id == 0:
            self.response.write(6)
            mgr.bot_2_id = 1
        else:
            self.response.write('false')

        mgr.updateDB()

class GameDBReset(webapp2.RequestHandler):
    def get(self):
        mgr = GameDataMgr()
        mgr.dataReset()
        self.response.write('clear success')

class Ready(webapp2.RequestHandler):
    def get(self):
        mgr = GameDataMgr()
        mgr.loadDB()
        
        strid = str(self.request.get_all('botid'))
        pos = strid.find('\'')
        
        botid = int(strid[pos+1])

        if botid == 5 and mgr.bot_1_id == 1:
            mgr.bot_1_rdy = 1
        elif botid == 6 and mgr.bot_2_id == 1:
            mgr.bot_2_rdy = 1
        else:
            self.response.write('ready fail')
            return

        self.response.write('on ready')
        mgr.updateDB()

class CheckAllReady(webapp2.RequestHandler):
    def get(self):
        mgr = GameDataMgr()
        mgr.loadDB()

        if mgr.bot_1_rdy == 1 and mgr.bot_2_rdy == 1:
            self.response.write('all ready')
        else:
            self.response.write('fail')

class AdminStart(webapp2.RequestHandler):
    def get(self):
        mgr = GameDataMgr()
        mgr.loadDB()

        if mgr.bot_1_rdy == 1 and mgr.bot_2_rdy == 1:
            self.response.write('start')
            mgr.game_start = 1
            mgr.updateDB()
        else:
            self.response.write('fail')

class GetStart(webapp2.RequestHandler):
    def get(self):
        mgr = GameDataMgr()
        mgr.loadDB()

        if mgr.game_start == 1:
            self.response.write('start')
        else:
            self.response.write('fail')

class Arrive(webapp2.RequestHandler):
    def get(self):
        mgr = GameDataMgr()
        mgr.loadDB()

        strbotid = str(self.request.get_all('botid'))
        strtime = str(self.request.get_all('time'))

        strbotpos = strbotid.find('\'')
        
        strtimepos = int(strtime.find('\'')) + 1
        endtimepos = int(strtime.find('\']'))


        botid = int(strbotid[strbotpos+1])

        strlength = endtimepos-strtimepos        
        strnum =""

        for i in range(strlength):
            strnum += strtime[strtimepos+i]

        if botid == 5:
            mgr.bot_1_time = int(strnum)
        elif botid == 6:
            mgr.bot_2_time = int(strnum)
        else:
            self.response.write('fail')
            return

        mgr.updateDB()
        self.response.write('success')

class GetArrive(webapp2.RequestHandler):
    def get(self):
        mgr = GameDataMgr()
        mgr.loadDB()

        self.response.write('bot 1 : ')
        output = ''
        if mgr.bot_1_time != 0:
            output = 'arrive'
        else:
            output = 'running'
            
        self.response.write(output+'<br>')
        self.response.write('bot 2 : ')

        if mgr.bot_2_time != 0:
            output = 'arrive'
        else:
            output = 'running'
        
        self.response.write(output+'<br>')

class GetArriveTime(webapp2.RequestHandler):
    def get(self):
        mgr = GameDataMgr()
        mgr.loadDB()

        self.response.write('bot 1 : ')
        self.response.write(str(mgr.bot_1_time)+'<br>')
        self.response.write('bot 2 : ')
        self.response.write(str(mgr.bot_2_time)+'<br>')        

class GetUserWin(webapp2.RequestHandler):
    def get(self):
        mgr = GameDataMgr()
        mgr.loadDB()

        strbotid = str(self.request.get_all('botid'))
        strbotpos = strbotid.find('\'')        
        botid = int(strbotid[strbotpos+1])

        a = 0
        b = 0

        if botid == 5:
            a = mgr.bot_1_time
            b = mgr.bot_2_time
        elif botid == 6:
            a = mgr.bot_2_time
            b = mgr.bot_1_time
        else:
            self.response.write('fail')
            return

        if a < b:
            self.response.write('win')
        elif a > b:
            self.response.write('lose')
        else:
            self.response.write('draw')

class GetAdminWin(webapp2.RequestHandler):
    def get(self):
        mgr = GameDataMgr()
        mgr.loadDB()

        if mgr.bot_1_time > mgr.bot_2_time:
            self.response.write('bot 2')
        elif mgr.bot_2_time > mgr.bot_1_time:
            self.response.write('bot 1')
        else:
            self.response.write('draw')            

app = webapp2.WSGIApplication([
    ('/', MainHandler),
    ('/user/getbotid', GetBotID),
    ('/user/ready', Ready),
    ('/user/start', GetStart),
    ('/user/arrive', Arrive),
    ('/user/win', GetUserWin),    
    ('/admin/reset', GameDBReset),
    ('/admin/start', AdminStart),
    ('/admin/getarrive', GetArrive),
    ('/admin/getarrivetime', GetArriveTime),    
    ('/admin/check_all_ready', CheckAllReady),
    ('/admin/getwin', GetAdminWin),    
], debug=True)
