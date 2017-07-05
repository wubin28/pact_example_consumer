示例，consumer 如何使用 pact 来：

1. 定义 mock server
1. 在运行测试时，pact 启动mock server, 并生成 pact 文件。

### 运行 Junit 测试
```
mvn test
```
也可以直接运行指定的测试类。


运行后，可看到生成的 pacts 文件在 target 目录下。
