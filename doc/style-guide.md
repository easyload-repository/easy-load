# Style Guide

## formatter

请使用 google-java-format, idea和eclipse都有插件.

## Spring MVC Style Guide

1. 对于Restful的API， 请使用`@RestController`。
2. 建议使用`@GetMapping·， ·@PostMapping` 而不是`@RequestMapping(methos = GET)`

## API design

### URL

1. 建议使用/api作为url的前缀，如/api/xxx/xxx
2. 建议使用`-` 来分隔字符，如/api/round-attender。 url不建议使用大小写进行区分。
3. 建议使用restful的命名规格
    * `GET /api/xxx` 代表获取xxx的所有记录,返回List
    * `POST /api/xxx` 代表添加xxx一条记录，其中body为xxx的json/xml形式,返回插入成功的对象
    * `DELETE /api/xxx/{id}` 代表删除某一条记录，其中id为该对象的id，返回删除对象
    * `PUT /api/xxx`，代表更新xxx某一条记录，其中body为xxx的json/xml形式，并且带有id.返回
4. 对于restful 不能满足的情况，可以参考以下
    * 使用 /search的方式作为查询。如`GET /api/xxx/search?q=query`代表查询xxx。或者使用POST方法，传入Example对象。
    * 使用 /*.do 的方式作为某些要做的action。 如 `api/xxx/start.do`.
    
    
### Response Status

在Spring中，可以用 `@ControllerAdvice` 或者 `@ResponseStatus` 来处理状态信息，而无需自己再封装一个。