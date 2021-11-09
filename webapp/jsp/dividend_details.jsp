<%@ page import="com.vision.accounts.model.Dividend" %>
<%@ page import="java.util.List" %>
<%@ page import="com.vision.accounts.model.DividendRecord" %>
<%@ page import="com.vision.accounts.bean.DividendBreakdownBean" %>
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
                        <%
                            Dividend dividend = (Dividend) request.getAttribute("dividend");
                        %>
                        <div class="col-lg-11">
                            <table class="table table-bordered table-hover table-striped">
                                <tr >
                                    <th colspan="6" style="color: green; text-align: center; font-size: 27px; font-weight: bold">Dividend Details with Money Receipt Dividend Breakup</th>
                                </tr>
                                <tr style="background-color: #a5bcaf">
                                    <th><b>Dividend Date :</b></th><th><%=dividend.getDividendDate()%></th>
                                    <th><b>No. of Beneficiary :</b></th><th><%=dividend.getTotalHolder()%></th>
                                    <th><b>Profit Rate :</b></th><th><%=dividend.getProfitRate()%></th>

                                </tr>
                                <tr><td colspan="6"> <hr/></td></tr>
                                <%
                                    @SuppressWarnings("unchecked")
                                    List<DividendRecord> recordList = (List<DividendRecord>) request.getAttribute("recordList");
                                    for (DividendRecord record : recordList) {
                                %>
                                <tr>

                                    <td colspan="6">
                                        <table class="table table-bordered table-hover table-striped">
                                            <%
                                                List<DividendBreakdownBean> list = record.getBreakdownList();
                                                int counter = 0;
                                                for (DividendBreakdownBean bean : list) {
                                                    if (counter == 0) {
                                            %>
                                                <tr style="background-color: #dddddd">
                                                    <td rowspan="<%=list.size()+1%>" style="width: 45%; background-color: #f3f3f3;">
                                                        <b>CID :</b> <%=record.getCustomer().getCid()%> <br>
                                                        <b>Name :</b> <%=record.getCustomer().getName()%> <br>
                                                        <b>Dividend :</b> <%="Tk."+record.getDividendAmt()+"/-"%> on
                                                        <%="Tk."+record.getDividendOnAmt()+"/-"%>
                                                    </td>
                                                    <td>MR</td>
                                                    <td>From</td>
                                                    <td>To</td>
                                                    <td>Days</td>
                                                    <td>On AMT</td>
                                                    <td>Dividend</td>
                                                </tr>

                                            <%
                                                }
                                            %>
                                            <tr>
                                                <td><%=bean.getReceipt()%>
                                                </td>
                                                <td><%=bean.getFrom()%>
                                                </td>
                                                <td><%=bean.getTo()%>
                                                </td>
                                                <td><%=bean.getDays()%>
                                                </td>
                                                <td class="amt"><%=bean.getAmountOn()%>
                                                </td>
                                                <td class="amt"><%=bean.getDividendAmt()%>
                                                </td>
                                            </tr>

                                            <%
                                                    counter++;
                                                }
                                            %>
                                        </table>

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
