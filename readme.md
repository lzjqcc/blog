##Jpa实现动态的更新
 1,在实体类上加上下面注解
 ```java
 @org.hibernate.annotations.Entity(selectBeforeUpdate = true,dynamicUpdate = true)
 ```
 2，在更新的方法中使用hibernate的Session进行更新，这个一定要在事务的环境下操作
  ```java
  SessionFactory sessionFactory=entityManager.getEntityManagerFactory().unwrap(SessionFactory.class);
  
   Session session= sessionFactory.getCurrentSession();
   session.update(obj)
  ```
  ### RequestParam
  如果前端传递的url中含有空格如，空格将会转义，SpringMVC将获取不到
  http://localhost:8080/friend/groupFriends?groupId = 1
 