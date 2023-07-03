<%-- 
    Document   : user-list
    Created on : 30-jun-2023, 18:47:09
    Author     : maike
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List" %>
<%@page import="cr.ac.cr.ie.domain.User" %>
<!DOCTYPE html>
<html>
    <head>
        <title>User List</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f9f9f9;
            }

            .edit-link {
                display: inline-block;
                padding: 8px 16px;
                background-color: #333;
                color: #fff;
                border: none;
                border-radius: 4px;
                text-decoration: none;
            }

            .edit-link:hover {
                background-color: #555;
            }

             .container {
        max-width: 800px;
        margin: 0 auto;
        padding: 20px;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        text-align: center;
    }

            h1 {
                text-align: center;
                color: #333;
            }

            table {
                border-collapse: collapse;
                width: 100%;
                margin-bottom: 20px;
            }

            th, td {
                border: 1px solid #ccc;
                padding: 10px;
                text-align: left;
            }

            th {
                background-color: #f2f2f2;
            }

            form {
                display: inline;
            }

            h2 {
                margin-top: 30px;
                color: #333;
            }

            label {
                display: inline-block;
                width: 80px;
                margin-right: 10px;
            }

            input[type="text"],
            input[type="email"] {
                width: 200px;
                padding: 5px;
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

            .alert {
                padding: 10px;
                background-color: #f44336;
                color: #fff;
                text-align: center;
                margin-bottom: 20px;
            }
            .add-button {
        display: inline-block;
        padding: 8px 16px;
        background-color: #333;
        color: #fff;
        border: none;
        border-radius: 4px;
        text-decoration: none;
        margin: 0 auto;
        text-align: center;
    }

    .add-button:hover {
        background-color: #555;
    }
        </style>
    </head>
    <body>
        <div class="container">
            <h1>User List</h1>

            <% if (request.getAttribute("message") != null) { %>
            <div class="alert">
                <%= request.getAttribute("message") %>
            </div>
            <% } %>

            <table>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Action</th>
                </tr>
                <% for (User user : (List<User>) request.getAttribute("users")) { %>
                <tr>
                    <td><%= user.getId() %></td>
                    <td><%= user.getName() %></td>
                    <td><%= user.getEmail() %></td>
                    <td>
                        <form action="users" method="post">
                            <input type="hidden" name="_method" value="DELETE" />
                            <input type="hidden" name="id" value="<%= user.getId() %>" />
                            <button type="submit">Delete</button>
                        </form>
                        <a href="users?action=edit&id=<%= user.getId() %>" class="edit-link">Edit</a>
                    </td>
                </tr>
                <% } %>
            </table>
            <a href="add-user.jsp" class="add-button">Add User</a>

        </div>
    </body>
</html>
