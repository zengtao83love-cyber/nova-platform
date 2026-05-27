# nova-bom

`nova-bom` is the single dependency version management module for `nova-platform`.

Current task: `T-02`.

This module intentionally contains only Maven `dependencyManagement` and version properties. It does not contain Java source code, runtime configuration, SQL, or business logic.

Confirmed version decisions:

| Dependency family | Version |
|---|---:|
| Spring Boot | 2.7.18 |
| MyBatis-Plus | 3.5.5 |
| MySQL Connector/J | 8.0.33 |
| JJWT | 0.11.5 |
| Knife4j | 4.3.0 |
| MapStruct | 1.5.5.Final |
| Lombok | 1.18.30 |
| Hutool | 5.8.26 |
| commons-lang3 | 3.13.0 |
| JUnit Jupiter | 5.10.1 |
| Mockito | 5.6.0 |
