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


```

    全局配置（TAG，各种格式化器...）或基于单条日志的配置
    支持打印任意对象以及可自定义的对象格式化器
    支持打印数组
    支持打印无限长的日志（没有 4K 字符的限制）
    XML 和 JSON 格式化输出
    线程信息（线程名等，可自定义）
    调用栈信息（可配置的调用栈深度，调用栈信息包括类名、方法名文件名和行号）
    支持日志拦截器
    保存日志文件（文件名和自动备份策略可灵活配置）
    在 Android Studio 中的日志样式美观
    简单易用，扩展性高

```






