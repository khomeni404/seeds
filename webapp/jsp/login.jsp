
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>SEEDS</title>

    <jsp:include page="stylesheet.jsp"/>

</head>

<body>

<div class="container">
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading" style="background-color: #cbddd8; min-height: 10%">
                    <h3 class="panel-title">
                        <div class="row">
                            <div class="col-lg-3">
                                <small>
                                    <i class="">
                                        <%--<img width="246" height="76" src="http://soft-engine.net/hub/images/seeds.png" alt=""/>--%>
                                        <img width="246" height="76" src="resources/images/seeds-2.png" alt=""/>
                                    </i>
                                    <!--<span style="font-size: 25px; color: black;">Islami Bank Bangladesh Limited</span>-->
                                </small>
                            </div>
                            <div class="col-lg-9 text-right">
                                <br/><br/><br/>
                                <span style="font-size: 26px" class="bolder green shine"><b>Soft Engine - Electronic Dividend System (SEEDS)</b></span>
                            </div>
                        </div>
                    </h3>
                </div>

                <div class="panel-body" style="min-height: 80%">

                    <div class="row">

                        <div class="col-lg-4 col-md-offset-4">
                            <div class="<#--login-panel--> panel panel-default">
                                <div class="panel-heading">
                                    <h3 class="panel-title">Log In</h3>
                                </div>
                                <div class="panel-body">
                                    <h5 style="color: red"><%=(String)request.getAttribute("msg")%></h5>
                                    <form role="form" name="loginForm" action="/seeds/auth/authenticateUser"
                                          method="POST">
                                        <fieldset>
                                            <div class="form-group">
                                                <input class="form-control" placeholder="Username" name="username"
                                                       type="username" value="mizan"
                                                       autofocus>
                                            </div>
                                            <div class="form-group">
                                                <input class="form-control" placeholder="Password" name="password"
                                                       type="password"
                                                       value="mizan123456">
                                            </div>
                                            <div class="checkbox">
                                                <label>
                                                    <input name="remember" type="checkbox" value="Remember Me">Remember
                                                    Me
                                                </label>
                                            </div>
                                            <!-- Change this to a button or input when using this as a form -->
                                            <a href="#" onclick="document.loginForm.submit()"
                                               class="btn btn-lg btn-success btn-block">Login</a>
                                        </fieldset>
                                    </form>

                                </div>

                            </div>

                        </div>
                        <div class="col-lg-12 text-right">
                            <img  src="resources/images/seeds_356212.gif" alt=""/>
                        </div>
                    </div>

                </div>

                <div class="panel-heading text-center" style="background-color: #05800a; min-height: 5%">
                    <span class="bigger-120" style="color: white">
							<span class="blue bolder"><b>SEEDS</b></span>
							| all right reserved to www.soft-engine.net &copy; 2003-2004
						</span>
                </div>

            </div>
        </div>
    </div>
</div>

<jsp:include page="scripts.jsp"/>

</body>

</html>
