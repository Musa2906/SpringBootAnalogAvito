<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>Flexbox</title>
</head>
<body>
<h1>Авито 0.2</h1><hr>
<h4>Панель Администратора</h4>
<table border="1">
    <tr>
        <th>Email</th>
        <th>Номер телефона</th>
        <th>Активность</th>
        <th>Роли</th>
        <th>Бан</th>
        <th>Редактирование</th>
        <th>Подробная информация</th>
        <th>Удалить</th>
    </tr>
    <#list users as user>
            <tr>
                <th>${user.email}</th>
                <th>${user.phoneNumber}</th>
                <th><#if user.active>true<#else>false</#if></th>
                <th><#list user.roles as role>${role}<#sep>, </#list></th>
                <th>
                    <form action="/admin/user/ban/${user.id}" method="post">
                        <input type="hidden" name="_csrf" value="${_csrf.token}">
                        <input type="submit" value="Бан">
                    </form>
                </th>
                <th><a href="/admin/user/edit/${user.id}">Редактировать</a></th>
                <th><a href="/user/${user.id}">Подробная информация</a></th>
                <th>
                    <form action="/admin/user/delete/${user.id}" method="post">
                        <input type="hidden" name="_csrf" value="${_csrf.token}">
                        <input type="submit" value="Удалить">
                    </form>
                </th>
            </tr>
    <#else>
        <h3>Нет пользователей</h3>
    </#list>
</table>

</body>
</html>