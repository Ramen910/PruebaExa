<%-- 
    Document   : add-user
    Created on : 02-jul-2023, 16:44:45
    Author     : maike
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Edit User</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f9f9f9;
            }

            .container {
                max-width: 800px;
                margin: 0 auto;
                padding: 20px;
            }

            h1 {
                text-align: center;
                color: #333;
            }

            form {
                width: 450px;
                margin: 0 auto;
                background-color: #fff;
                padding: 20px;
                border: 1px solid #ccc;
                border-radius: 4px;
            }

            label {
                display: block;
                margin-bottom: 15px;
                margin-top: 15px;
            }

            input[type="text"],
            input[type="email"] {
                width: 100%;
                height: 30px;
                border: 1px solid #ccc;
                border-radius: 4px;
            }

            button[type="submit"] {
                margin-top: 10px;
                padding: 8px 16px;
                background-color: #333;
                color: #fff;
                border: none;
                border-radius: 4px;
                cursor: pointer;
            }

            button[type="submit"]:hover {
                background-color: #555;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h1>Add User</h1>
            <form action="users" method="post">
                <label for="name">Name:</label>
                <input type="text" name="name" id="name" required /><br />
                <label for="email">Email:</label>
                <input type="email" name="email" id="email" required /><br />
                <button type="submit">Add User</button>
            </form>
        </div>
    </body>
</html>
