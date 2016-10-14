'''
Created on 2016年9月22日

@author: abc
'''
import os

def app(environ, start_response):
    """A barebones WSGI application.
    This is a starting point for your own Web framework :)
    """

    response_headers = [('Content-Type', 'text/html')]
    fullPath = environ['PATH_INFO']
    
    #判断静态/动态
    if isStatic(environ['PATH_INFO']):
        if  os.path.exists(fullPath.lstrip('/')) :#文件存在
            status = '200 OK'
            start_response(status, response_headers)
            return showhtml(fullPath.lstrip('/'))
        else:#文件不存在
            status = '404 NOT FOUND'
            start_response(status, response_headers)
            return errorPage(fullPath.lstrip('/'))
    else :
        status = '200 OK'
        start_response(status, response_headers)
        return ['Hello ', environ['PATH_INFO']]
    
def isStatic(url):
    (filepath,tempfilename) = os.path.split(url);
    (shotname,extension) = os.path.splitext(tempfilename);
    print (extension)
    if extension == '.html':
        return True
    else:
        return False

def showhtml(fullPath):
    # 打开一个文件
    fo = open(fullPath, "r+")
    str = fo.read();
    print ("读取的字符串是 : ", str)
    # 关闭打开的文件
    fo.close()
    return str
    
def errorPage(fullPath):
    Error_Page = """\
<html>
<body>
<h1>Error accessing </h1>
<h2>{path}</h2>
</body>
</html> """.format(path=fullPath)
    return Error_Page    