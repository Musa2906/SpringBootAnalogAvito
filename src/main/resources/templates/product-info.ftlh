<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>Flexbox</title>
</head>
<body>



<h3>Подробная инвормания о товаре</h3>
<#list images as img>
    <img src="/images/${img.id}" height="60px"/><br><br>
</#list>

<b>Название товара</b>${product.title}<br>
<b>Описание товара</b>${product.description}<br>
<b>Цена</b>${product.price}<br>
<b>Город</b>${product.city}<br>
<b>Автор</b><a href="/user/${product.user.id}">${product.user.name}</a><br>


</body>
</html>

