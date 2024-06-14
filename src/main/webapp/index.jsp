<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
            crossorigin="anonymous"></script>
</head>
<body>

<div class="container" th:replace="fragments/header :: header">
    <!-- ============================================================================ -->
    <!-- This content is only used for static prototyping purposes (natural templates)-->
    <!-- and is therefore entirely optional, as this markup fragment will be included -->
    <!-- from "fragments/header.html" at runtime.                                     -->
    <!-- ============================================================================ -->
</div>
<div class="container">
    <div class="hero-unit">
        <h1>Test</h1>
        <p>
            Welcome to the Spring MVC Quickstart application!
            Get started quickly by signing up.
        </p>
        <p>
            <a href="/signup" th:href="@{/signup}" class="btn btn-large btn-success">Sign up</a>
        </p>
    </div>
    <div th:replace="fragments/footer :: footer">&copy; 2016 The Static Templates</div>
</div>
</body>
</html>
