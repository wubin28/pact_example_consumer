## 示例，consumer 如何使用 pact 来：

1. 定义 mock server
1. 在运行测试时，pact API 启动mock server, 并生成 pact 文件。


运行后，可看到生成的 pacts 文件在 target 目录下。


## 运行此服务：
先直接运行 Applicaiton, 然后
http get /  或者  /foos，
/foos 功能的完成依赖于另一个服务,所以分别称他们 consumer/provider;
注意，application.properties 配置了 provider，我们在这里配置了 mock service 的URL。

## 对consumer执行测试有两种方式：
1. 直接测试 consumer 的外部接口  get /foos。 参考 ApplicationTest , 需要先启动 Application 服务
2. 调用客户的代理; 参考 ConsumerPortTest.




