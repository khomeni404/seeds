<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>SEEDS</title>

    <jsp:include page="stylesheet.jsp"/>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
</head>

<body>

<div class="container">
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <jsp:include page="header.jsp"/>
                <div class="panel-body" style="min-height: 80%">
                    <div class="row">
                        <div class="col-lg-1">
                            <jsp:include page="menu.jsp"/>
                        </div>
                        <div class="col-lg-11 text-center">
                            <div class="row">

                                <div class="col-lg-2"></div>
                                <div class="col-lg-7">
                                    <div class="panel panel-default">
                                        <div class="panel-heading">
                                            <h3 class="panel-title">New Dividend Deceleration & Execution</h3>
                                        </div>
                                        <div class="panel-body">
                                            <h5 style="color: red"><%=(String) request.getAttribute("msg")%>
                                            </h5>
                                            <form role="form" name="loginForm" action="/seeds/dividend/sendSms"  enctype="text/html; charset=UTF-8"
                                                  method="POST">
                                                <fieldset>

                                                    <div class="form-group input-group" style="width: 100%">
                                                        <span class="input-group-addon"
                                                              style="width: 30%; text-align: right;  font-weight: bold; ">Text :</span>
                                                        <input type="text" class="form-control" name="content"
                                                               id="declare_date" value="&#2453;&#2494;&#2478;&#2494;&#2482; &#2477;&#2494;&#2439; &#2453;&#2503;&#2478;&#2472; ?" style="width: 100%"
                                                               placeholder="">
                                                    </div>

                                                    <a href="#" onclick="document.loginForm.submit();"
                                                       class="btn btn-lg btn-danger btn-block">Execute</a>
                                                </fieldset>
                                            </form>

                                        </div>

                                    </div>

                                </div>
                            </div>

                        </div>
                    </div>
                </div>

                <jsp:include page="footer.jsp"/>
            </div>
        </div>
    </div>
</div>

<jsp:include page="scripts.jsp"/>
</body>

</html>

<script>
    function submitForm() {
        if (validate()) {
            document.loginForm.submit();
        }
    }

    function validate() {
        return (
            isEmpty($('#dividendId'))
            && isEmpty($('#execution_date'))
            && isEmpty($('#dividend_date'))
            && isEmpty($('#declare_date'))
            && isEmpty($('#profit_rate'))
        )
    }
</script>
