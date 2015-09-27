<link rel="stylesheet" href="styles/styleGreen.css" type="text/css" media="screen" />

<?php
include 'db.php';
?>
</div>
<div align="center">
	<table border="0">
		<form enctype="multipart/form-data" action="post.php" method="post">
			<textarea style="overflow:auto;resize:none" name="msg" maxlength="192" rows="10" cols="30" class="input"></textarea><br>
			<input type="hidden" name="MAX_FILE_SIZE" value="100000"  />
			<input name="uploadedfile" type="file" /><br/>
			<input type="submit" value="Send" class="try">
		</form>
		<?php

		$user=(isset($_SESSION['user']) ? $_SESSION['user'] : null);
$resultZ = mysqli_query($con,"SELECT uid FROM users where username='".$user."'");//get uid
$rowZ = mysqli_fetch_array($resultZ);
if($rowZ['uid']>1)
	$result = mysqli_query($con,"SELECT mid,username,message FROM messages inner join users on messages.owner=users.uid where messages.owner not in(select uid_ign from ignored where uid=".$rowZ['uid'].") order by mid desc limit 0,20");
else
	$result = mysqli_query($con,"SELECT mid,username,message FROM messages inner join users on messages.owner=users.uid order by mid desc limit 0,20");

while($row = mysqli_fetch_array($result))
{
	echo "<tr><td><a class=\"subtext\" href=\"ignore.php?user=".$row['username']."\">".$row['username']." </a></td><td width=\"500\">".base64_decode($row['message'])."</td><td>";

//if the post has image
	$images = mysqli_query($con,"SELECT name FROM images where mid='".$row['mid']."'");
	$imagesRow = mysqli_fetch_array($images);
	if(isset($imagesRow['name'])){
		echo "<img src=\"uploads/".$imagesRow['name']."\" height=\"50\" width=\"50\">";
	}


	echo "</td><td width=\"100\">";
	$tags = mysqli_query($con,"select distinct(title) as title,msg_lbl.mid,msg_lbl.lid from msg_lbl inner join labels on msg_lbl.lid=labels.lid where msg_lbl.mid=".$row['mid']." order by importance limit 0,5");
	while($rowX = mysqli_fetch_array($tags))
		{echo $rowX['title']." ";}
	?>
</td>
<td width="200">
	<form action="tag.php" method="post">
		<textarea style="overflow:auto;resize:none" name="tag" maxlength="15" rows="1" cols="15" class="input"></textarea>
		<input type="hidden" name="mid" value="<?php echo $row['mid']; ?>" />
		<input type="submit" value="Tag"  class="try">
	</form>
</tr>
<?php
}
?>
</table>
</div>
