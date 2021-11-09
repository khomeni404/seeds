<%@ page import="com.vision.accounts.model.Dividend" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="java.math.RoundingMode" %>
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
                <jsp:include page="header.jsp"/>
                <div class="panel-body" style="min-height: 80%">
                    <div class="row">
                        <div class="col-lg-1">
                            <jsp:include page="menu.jsp"/>
                        </div>
                        <div class="col-lg-11">
                            <table class="table table-bordered table-hover table-striped">
                                <tr>
                                    <th>DID</th>
                                    <th>Declare Date</th>
                                    <th>Dividend Date</th>
                                    <th>Profit Rate</th>
                                    <th>Holder</th>
                                    <th>Dividend On</th>
                                    <th>Dividend Amt</th>
                                    <th class="text-center">Total</th>
                                    <th></th>
                                </tr>
                                <%
                                    DecimalFormat df = new DecimalFormat("##,##,##,###.00");
                                    df.setRoundingMode(RoundingMode.CEILING);
                                    @SuppressWarnings("unchecked")
                                    List<Dividend> dividendList = (List<Dividend>) request.getAttribute("dividendList");
                                    Double cumuAmt = 0.0;
                                    for (Dividend d : dividendList) {
                                        Double divAmt = d.getTotalDividendAmt();
                                        cumuAmt +=divAmt;
                                %>
                                <tr>
                                    <td><%=d.getId()%>
                                    </td>
                                    <td><%=d.getDeclareDate()%>
                                    </td>
                                    <td><%=d.getDividendDate()%>
                                    </td>
                                    <td class="amt"><%=df.format(d.getProfitRate())%>
                                    </td>
                                    <td><%=d.getTotalHolder()%>
                                    </td>
                                    <td class="amt"><%=df.format(d.getTotalAmountOn())%>
                                    </td>

                                    <td class="amt"><%=df.format(divAmt)%>
                                    </td>
                                    <td class="amt"><%=df.format(cumuAmt)%>
                                    </td>
                                    <td>
                                        <a href="/seeds/dividend/getDividendRecordList?dividendId=<%=d.getId()%>"
                                           class="btn btn-sm btn-success btn-block">view</a>
                                    </td>
                                </tr>
                                <%
                                    }
                                %>
                            </table>
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
