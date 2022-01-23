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
                                            <form role="form" name="loginForm" action="/seeds/dividend/execute"
                                                  method="POST">
                                                <fieldset>
                                                    <%--declare_date, dividend_date, execution_date, profit_rate,
                                                     total_holder, total_amt_on, total_dividend_amt)" +--%>
                                                    <%--<div class="form-group input-group"  style="width: 100%">
                                                        <span class="input-group-addon" style="width: 30%; text-align: right;  text-align: right; font-weight: bold; ">Dividend ID :</span>
                                                        <div><input type="text" class="form-control" id="dividendId" name="dividendId" value="2010630"  style="width: 100%" placeholder="declare_date"></div>
                                                    </div>--%>

                                                    <div class="form-group input-group" style="width: 100%">
                                                        <span class="input-group-addon"
                                                              style="width: 30%; text-align: right;  font-weight: bold; ">Project :</span>
                                                        <select name="projectId" id="" class="form-control">
                                                        <c:forEach var="project" items="${projects}">
                                                            <option value="<c:out value="${project.id}"/>"><c:out value="${project.projectName}"/></option>
                                                        </c:forEach>

                                                        </select>
                                                    </div>

                                                    <div class="form-group input-group" style="width: 100%">
                                                        <span class="input-group-addon"
                                                              style="width: 30%; text-align: right;  font-weight: bold; ">Declare Date :</span>
                                                        <input type="text" class="form-control" name="declare_date"
                                                               id="declare_date" value="05-07-2021" style="width: 100%"
                                                               placeholder="05-07-2016">
                                                    </div>
                                                    <div class="form-group input-group" style="width: 100%">
                                                        <span class="input-group-addon"
                                                              style="width: 30%; text-align: right;  font-weight: bold; ">Dividend Date :</span>
                                                        <input type="text" class="form-control" id="dividend_date"
                                                               name="dividend_date" value="30-10-2021"
                                                               placeholder="30-06-2011" style="width: 100%">
                                                    </div>
                                                    <div class="form-group input-group" style="width: 100%">
                                                        <span class="input-group-addon"
                                                              style="width: 30%; text-align: right;  font-weight: bold; ">Execution Date :</span>
                                                        <input type="text" class="form-control"
                                                               placeholder="execution_date" id="execution_date"
                                                               name="execution_date" value="${executionDate}"
                                                               style="width: 100%">
                                                    </div>
                                                    <div class="form-group input-group" style="width: 100%">
                                                        <span class="input-group-addon"
                                                              style="width: 30%; text-align: right;  font-weight: bold; ">Profit Rate :</span>
                                                        <input type="text" class="form-control" id="profit_rate"
                                                               name="profit_rate" value="3.0" placeholder="3.0"
                                                               style="width: 100%">
                                                    </div>


                                                    <!-- Change this to a button or input when using this as a form -->
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
