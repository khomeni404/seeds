<%@ page import="com.vision.accounts.model.DividendRecord" %>
<%@ page import="java.util.List" %>
<%@ page import="com.vision.accounts.bean.DividendBreakdownBean" %>
<html>
<body>
<%
    @SuppressWarnings("unchecked")
    List<DividendRecord> recordList = (List<DividendRecord>) request.getAttribute("recordList");
%>

<table border="1">
    <tr>
        <th width="20%"></th>
        <th width="20%"></th>
    </tr>
    <%
        for (DividendRecord record : recordList) {
    %>
    <tr>
        <td>
            CID : <%=record.getCustomer().getCid()%> <br>
            Name : <%=record.getCustomer().getName()%> <br>
            Dividend : <%=record.getDividendAmt()%> on
            <%=record.getDividendOnAmt()%>
        </td>
        <td>
            <table>
                <%
                    List<DividendBreakdownBean> list = record.getBreakdownList();
                    for (DividendBreakdownBean bean : list) {
                %>


                <tr>
                    <td><%=bean.getReceipt()%>
                    </td>
                    <td><%=bean.getFrom()%>
                    </td>
                    <td><%=bean.getReceipt()%>
                    </td>
                    <td><%=bean.getReceipt()%>
                    </td>
                    <td><%=bean.getReceipt()%>
                    </td>
                </tr>

                <%
                    }
                %></table>

        </td>
    </tr>
    <%
        }
    %>
</table>

</body>
</html>