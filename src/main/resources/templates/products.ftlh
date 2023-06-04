<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>Flexbox</title>
</head>
<body>
<h1>Авито 0.2</h1><hr>
<#if user.email??>
<h3><i>${user.name}</i></h3>
<form action="/logout" method="post">
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="Выйти"/>
</form>
    <#if user.isAdmin()>
        <a href="/admin">Панель Администратора</a>
    </#if>
<#else>
<a href="/login">Войти</a>
</#if>



<form action="/" method="get">
    Поиск по названию объявления: <input type="text" name="title"><br>
    <input type="submit" value="Поиск"/>
</form>

<#list products as product>
    <div>
        <p><b>${product.title}</b> ${product.price} руб. | <a href="/product/${product.id}">Подробнее</a></p>
    </div>
    <#else><!--если нету ни одного товара то-->
    <h3>Товаров нет</h3>
</#list>

<hr>

<#if user.email??>
<h3>Создать новый товар</h3>
<form action="/product/create" method=post enctype="multipart/form-data">
    Название объявления: <input type="text" name="title"/><br><br>
    Описание: <input type="text" name="description"/><br><br>
    Цена: <input type="number" name="price"/><br><br>
    Город: <input type="text" name="city"/><br><br>
    Первая фотография: <input type="file" name="file1"/><br><br>
    Вторая фотография: <input type="file" name="file2"/><br><br>
    Третья фотография: <input type="file" name="file3"/><br><br>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="Добавить товар">
</form>
</#if>

</body>
</html>

