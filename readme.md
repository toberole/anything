# Failed to connect to github.com port 443
```

Failed to connect to github.com port 443

建立一个github帐号(pythonsoft)之后，按照提示create a repository: test.

想把这个项目clone到本地:

git clone https://github.com/pythonsoft/test.git

出现错误：

unable to access 'https://github.com/pythonsoft/test.git: Failed to connect to github.com port 443:Time out‘

原因是没有输入指定用户名和密码：

打开终端，切换到被git管理项目的 .git 目录

vi config[编辑config文件]

把url = https://github.com/pythonsoft/test.git

改成url = https://用户名:密码@github.com/pythonsoft/test.git。


```






