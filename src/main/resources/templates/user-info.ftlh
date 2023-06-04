<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>Flexbox</title>
</head>
<body>
<h3>Информация о пользователе</h3>
<b>Имя пользователя</b>${user.name}<br>
<b>Email пользователя</b>${user.email}<br>
<b>Номер телефона:</b>${user.phoneNumber}<br>

<#list products as product>
    <div>
        <p><img src="/images/${product.previewImageId}" height="30px"/><b>${product.title}</b> ${product.price} руб. | <a href="/product/${product.id}">Подробнее</a></p>
    </div>

<#else>
</#list>

</body>
</html>
