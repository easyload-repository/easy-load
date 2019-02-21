# Practice

## JPA Practice

1. 对于`@OneToMany`或者`@ManyToMany`注解的类, 建议初始化一个空的list。防止没有数据空指针异常。
```java
@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "activityId", referencedColumnName = "activityId")
  private List<ELRound> rounds = new ArrayList<>();
```
