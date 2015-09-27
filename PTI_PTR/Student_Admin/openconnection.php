<?php

	DEFINE ('DBUSER','root');
	DEFINE ('DBPW','root');
	DEFINE ('DBHOST','localhost');
	DEFINE ('DBNAME','sat');

	class DB{
	private static $mysqli;
	public static function get(){
		if(!isset(self::$mysqli)){
			self::$mysqli= mysqli_connect(DBHOST,DBUSER,DBPW,DBNAME) or die("Error ".mysqli_error($mysqli));
			if(!mysqli_select_db(self::$mysqli,DBNAME))
		{
			trigger_error("Could not select the database <br/>");
			exit();
		}
		
		
	
		}
		return self::$mysqli;
		}
	}
			//scrape out potentially harmful info ; sql injection
		
			function escape_data($data){
				$mysqli= DB::get();
				$data = mysqli_escape_string($mysqli,trim($data));
				$data=strip_tags($data);
		
			return $data;
			}

?>

