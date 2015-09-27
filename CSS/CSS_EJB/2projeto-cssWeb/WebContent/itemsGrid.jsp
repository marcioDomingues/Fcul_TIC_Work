<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<c:forEach var="item" items="${items}">
	<div class="draggable eMedium" item-id='<c:out value="${item.id}" />'>
		<img src='${item.thumb}'/>
		<span><c:out value="${item.title}" /></span> - <span><c:out
				value="${item.author}" /></span> - <span> <c:out value="${item.id}" /></span>

		<button class="btn btn-xs btn-default removeFromShelf removeBtn ">x</button>
	</div>
</c:forEach>

<script type="text/javascript">
	$(function() {

		var initDraggables = function() {
			$('.draggable').each(function(i, elem) {
				$(elem).data({
					'originalLeft' : $(elem).css('left'),
					'origionalTop' : $(elem).css('top')
				});
			});
			
			//function to reset an element position
			var resetElementPosition = function(elem) {
				$(elem).css({
					'left' : $(elem).data('originalLeft'),
					'top' : $(elem).data('origionalTop')
				});
			};

			//sets all .draggable 
			$(".draggable").draggable({
				drop : function(event, ui) {
					$(this).addClass("ui-state-highlight");
				}
			});
			
			//sets beahviour of each .droppable
			$(".shelf.extra").droppable( 
					{
						drop : function(event, ui) {
							$(this).addClass("dropped-in");


							//resets draggable to original position
							resetElementPosition(ui.draggable);

							//prepares ajax call
							var itemID = $(ui.draggable).attr('item-id');
							var shelfName = $(this).attr('shelf');
							var data = "itemId=" + itemID + "&" + "shelf="
									+ shelfName;

							var callback = function(response) {
								$(".shelf.dropped-in").addClass("itemAdded");
								setTimeout(function(){
									$(".shelf").removeClass("itemAdded");
									$(".shelf").removeClass("dropped-in");
								}, 2000);
							};

							ajaxCall("POST", "DraggedItemToShelfEvent", data,
									callback);

						}
					});
		}

		//finally, sets the loaded draggables
		initDraggables();
		//and waits for changes on itemsContainer to init the new items

	});
</script>
<script type="text/javascript">
	$(document).ready(function() {
		/* REMOVES AN ITEM FROM THE SELECTED SHELF */
		$(".removeFromShelf").click(function(e) {
			e.preventDefault();
			
			//gets selected shelf name
			var shelf = $('.shelf.selected').attr('shelf');
			//gets item whose remove button was pressed
			$item = $(this).closest('.draggable');
			$item.addClass('itemToBeRemoved');
			//gets itemId
			var itemId = $item.attr('item-id');
			
			//builds data to ajax call
			var data = "itemId=" + itemId + "&" + "shelf=" +shelf;
			var callback = function(response) {
				$('.itemToBeRemoved').remove();
			};

			ajaxCall("POST", "RemoveItemFromShelfEvent", data, callback);

			return false;

		});
	});
</script>