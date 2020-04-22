<html>
   <head>
      <style>
         .div1 {
         margin-left: 25%;
         margin-right: 25%;
         width: auto;
         height: 30px;
         border: 1px solid black;
         }
         .div2 {
         margin-left: 25%;
         margin-right: 25%;
         width: auto;
         height: 190px;
         padding: 50px;
         border: 1px solid black;
         }
         body {
         font-size: 20px;
         }
         .center {
         margin-right: 300;
         margin-bottom: 10;
         margin: auto;
         width: 250px;
         height: 100px;
         border: 2px solid #73AD21;
         padding: 7px;
         }
         table,
         th,
         td {
         margin-left: 25%;
         border: 1px solid black;
         border-collapse: collapse;
         }
         th,
         td {
         padding: 5px;
         text-align: left;
         }
      </style>
   </head>
   <body>
      <div class="div1">
         <div class="wordart rainbow">
            <center>IMAGINATION</center>
         </div>
      </div>
      <div class="div2">
         <div class="center" style="width: auto;">
            <center><span style='font-size:40px;'>&#10004;</span></center>
            <center>
               <p>Documents Completed</P>
            </center>
         </div>
         <p>Requested by:
            <br> ${requestedBy}
            <br> ${mailAddress}
         </p>
      </div>
      <br>
      <div style="width: auto;">
      
         <table id="cutsmId" style="float:left; width:50%; margin-right: 20px">
            <tr>
               <th colspan="2">
                  <center>Click <a href="${contractorPdf}"  target="_blank">here</a> to download signed contract agreement copy</center>
               </th>
            </tr>
         </table>
      </div>
      <style></style>
   </body>
</html>