<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
<!-- Optional theme -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap-theme.min.css">
<!-- Latest compiled and minified JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="/2projeto-cssWeb/assets/style/style.css">

<title>CSS</title>
</head>
<body>

	<div class="container-fluid fullHeight">
		<!-- If Needed Left and Right Padding in 'md' and 'lg' screen means use container class -->
		<div class="fullHeight">
			<div class="col-xs-4 col-sm-4 col-md-3 col-lg-3 sidebar fullHeight">

				<div>
					<h3>CSS Library</h3>
					<ul>
						<li class=".library"><a href="#" onclick="location.reload()">Library
								Items</a></li>
					</ul>
				</div>
				<div>
					<h3>Shelves</h3>
					<ul>
						<li class="droppable shelf mainShelf"
							shelf='<c:out value="${mainShelf}" />'><a href="#"><c:out
									value="${mainShelf}" /></a></li>
						<c:forEach var="shelf" items="${shelves}">
							<li class="droppable shelf extra"
								shelf='<c:out value="${shelf}" />'><a href="#"><c:out
										value="${shelf}" /></a>
								<button class="btn btn-xs btn-default removeShelf removeBtn">x</button>
							</li>

						</c:forEach>
						<li class="addShelfRow">
							<form action="AddNormalShelf" method="post">
								<input name="name" type="text" placeholder="New Shelf Name">
								<input type="submit" value="Add">
							</form>
						</li>
					</ul>
				</div>
			</div>


			<div class="col-xs-7 col-sm-7 col-md-9 col-lg-9">
				<h3>Current Container</h3>
				<div id="itemsContainer" class="items container">
					<c:forEach var="item" items="${items}">
						<div class="draggable eMedium lendable"
							item-id='<c:out value="${item.id}" />'>
							<span><c:out value="${item.title}" /></span> - <span><c:out
									value="${item.author}" /></span> - <span> <c:out
									value="${item.id}" /></span>
						</div>
					</c:forEach>
				</div>
			</div>

		</div>
	</div>

	<!-- 	AJAX call to show items of the selected Shelf -->
	<script type="text/javascript">
		$(document).ready(
				function() {

					$(".shelf").click(
							function(e) {
								e.preventDefault();

								//gets the selected shelf name
								name = $(this).attr('shelf');
								//creates a callback function 
								var callback = function(response) {
									$("#itemsContainer").html(response);
								};
								//procedes with ajax call to get this shelfs rentals
								ajaxCall("GET", "RetrieveItemsHandler", "name="
										+ name, callback);

								//by last, sets this as selected
								$(".shelf.selected").removeClass('selected');
								$(this).addClass('selected');

								return false;

							});
				});
	</script>

	<script type="text/javascript">
		function ajaxCall(method, url, data, callback) {
			$.ajax({
				type : method,
				url : url,
				data : data,
				success : function(response) {
					callback(response);
				}
			});
			return false;
		}
	</script>
	<!-- 	AJAX call to delete a certain Shelf -->
	<script type="text/javascript">
		$(document).ready(function() {

			/* REMOVES A SHELF */
			$(".removeShelf").click(function(e) {
				e.preventDefault();
				var name = $(this).parent().attr('shelf');
				var callback = function(response) {
					location.reload();
				};

				ajaxCall("POST", "RemoveShelf", "name=" + name, callback);

				return false;

			});

			/* REMOVES AN ITEM FROM THE SELECTED SHELF */
			$(".removeFromShelf").click(function(e) {
				e.preventDefault();
				var shelf = $('shelf.selected').parent().attr('shelf');
				var itemId = $(this).closest('.draggable').attr('item-id');
				var callback = function(response) {
					location.reload();
				};

				ajaxCall("POST", "RemoveShelf", "name=" + name, callback);

				return false;

			});
		});
	</script>

	<script type="text/javascript">
		$(function() {
			//function to reset an element position
			var resetElementPosition = function(elem) {
				$(elem).css({
					'left' : $(elem).data('originalLeft'),
					'top' : $(elem).data('origionalTop')
				});
			};

			var initDraggables = function() {
				$('.draggable').each(function(i, elem) {
					$(elem).data({
						'originalLeft' : $(elem).css('left'),
						'origionalTop' : $(elem).css('top')
					});
				});

				//sets all .draggable 
				$(".draggable").draggable({

					stop : function(event, ui) {
						resetElementPosition(ui.draggable);
					}
				});
			}

			//finally, sets the loaded draggables
			initDraggables();
			//and waits for changes on itemsContainer to init the new items

			//sets beahviour of the mainShelf
			$(".mainShelf").droppable({
				drop : function(event, ui) {
					$(this).addClass("dropped-in");

					//resets draggable to original position
					resetElementPosition(ui.draggable);

					var itemID = $(ui.draggable).attr('item-id');
					var data = "itemId=" + itemID;
					var callback = function(response) {
						$(".shelf.dropped-in").addClass("itemAdded");
						setTimeout(function() {
							$(".shelf").removeClass("itemAdded");
							$(".shelf").removeClass("dropped-in");
						}, 2000);
					};

					ajaxCall("POST", "RentItemEvent", data, callback);

				}
			});
			//sets beahviour of the mainShelf
			$(".shelf.extra").droppable({
				drop : function(event, ui) {
					if ( $(ui.draggable).hasClass('lendable') ) {
						alert('Sorry, you must rent this item first and then move it to here from the MyRentals shelf.');
						resetElementPosition(ui.draggable);
					}
						
				}
			});
		});
	</script>


</body>
</html>