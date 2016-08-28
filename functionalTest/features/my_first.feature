Feature: Login feature

  Scenario: As a valid user I can log into my app
    When I press "清除内容"
    And I press "预览内容"
    And I enter url "http://mp.weixin.qq.com/s?__biz=MjM5NzAwNDI4Mg==&mid=2652190959&idx=1&sn=9b1f9fe6525be0dbcfb16cd656c85e14&scene=4#wechat_redirect"
    Then I see "发送"

