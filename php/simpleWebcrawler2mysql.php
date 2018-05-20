<?php
include('simple_html_dom.php');
$con = mysql_connect("localhost","root","123456");
if (!$con)
{
  die('Could not connect: ' . mysql_error());
  return;
}
echo('ok');
mysql_select_db("finance", $con);
$fundCodes=array('040010','121001','000251','217010','340001');
//$fundCodes=array('217010');
foreach($fundCodes as $code)
{
	$url='http://quotes.money.163.com/fund/jjzl_'.$code.'.html';		
	$html = file_get_html($url);
	if($html)
	{	
		$div = $html->find('h3 big'); 
		$value = (float)$div[0]->innertext;		
		$insert = "INSERT INTO bb_found (date, code, value) VALUES (now(), '$code', '$value')";
		mysql_query($insert);
		echo $insert;
		sleep(1);
	}
}
mysql_close($con);
 ?>
