<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>Flexbox</title>
</head>
<body>

<h1>Авторизация</h1>
<form action="/login" method="post">
    Эл. почта: <input type="email" name="username"><br><br>
    Пароль: <input type="password" name="password"><br><br>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="Вход"/>
</form>
<a href="/registration">Зарегестрироваться</a>
</body>
</html>