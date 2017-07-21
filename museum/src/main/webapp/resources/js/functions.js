/* BoxWidget
 * =========
 * BoxWidget is a plugin to handle collapsing and
 * removing boxes from the screen.
 *
 * @type Object
 * @usage $.SystemApp.boxWidget.activate()
 *        Set all your options in the main $.SystemApp.options object
 */
$.SystemApp.boxWidget = {
	selectors : {
		// Remove button selector
		remove : '[data-widget="remove"]',
		// Collapse button selector
		collapse : '[data-widget="collapse"]'
	},
	icons : {
		// Collapse icon
		collapse : 'fa-minus',
		// Open icon
		open : 'fa-plus',
		// Remove icon
		remove : 'fa-times'
	},
	animationSpeed : 500,
	activate : function(_box) {
		var _this = this;
		if (!_box) {
			_box = document; // activate all boxes per default
		}
		// Listen for collapse event triggers
		$(_box).on('click', _this.selectors.collapse, function(e) {
			e.preventDefault();
			_this.collapse($(this));
		});

		// Listen for remove event triggers
		$(_box).on('click', _this.selectors.remove, function(e) {
			e.preventDefault();
			_this.remove($(this));
		});
	},
	collapse : function(element) {
		var _this = this;
		// Find the box parent
		var box = element.parents(".box").first();
		// Find the body and the footer
		var box_content = box
				.find("> .box-body, > .box-footer, > form  >.box-body, > form > .box-footer");
		if (!box.hasClass("collapsed-box")) {
			// Convert minus into plus
			element.children(":first").removeClass(_this.icons.collapse)
					.addClass(_this.icons.open);
			// Hide the content
			box_content.slideUp(_this.animationSpeed, function() {
				box.addClass("collapsed-box");
			});
		} else {
			// Convert plus into minus
			element.children(":first").removeClass(_this.icons.open).addClass(
					_this.icons.collapse);
			// Show the content
			box_content.slideDown(_this.animationSpeed, function() {
				box.removeClass("collapsed-box");
			});
		}
	},
	remove : function(element) {
		// Find the box parent
		var box = element.parents(".box").first();
		box.slideUp(this.animationSpeed);
	}
};

/*
 * ------------------ - Custom Plugins - ------------------ All custom plugins
 * are defined below.
 */

/*
 * BOX REFRESH BUTTON ------------------ This is a custom plugin to use with the
 * component BOX. It allows you to add a refresh button to the box. It converts
 * the box's state to a loading state.
 * 
 * @type plugin @usage $("#box-widget").boxRefresh( options );
 */
(function($) {

	"use strict";

	$.fn.boxRefresh = function(options) {

		// Render options
		var settings = $.extend({
			// Refresh button selector
			trigger : ".refresh-btn",
			// File source to be loaded (e.g: ajax/src.php)
			source : "",
			// Callbacks
			onLoadStart : function(box) {
				return box;
			}, // Right after the button has been clicked
			onLoadDone : function(box) {
				return box;
			} // When the source has been loaded

		}, options);

		// The overlay
		var overlay = $('<div class="overlay"><div class="fa fa-refresh fa-spin"></div></div>');

		return this
				.each(function() {
					// if a source is specified
					if (settings.source === "") {
						if (window.console) {
							window.console
									.log("Please specify a source first - boxRefresh()");
						}
						return;
					}
					// the box
					var box = $(this);
					// the button
					var rBtn = box.find(settings.trigger).first();

					// On trigger click
					rBtn.on('click', function(e) {
						e.preventDefault();
						// Add loading overlay
						start(box);

						// Perform ajax call
						box.find(".box-body").load(settings.source, function() {
							done(box);
						});
					});
				});

		function start(box) {
			// Add overlay and loading img
			box.append(overlay);

			settings.onLoadStart.call(box);
		}

		function done(box) {
			// Remove overlay and loading img
			box.find(overlay).remove();

			settings.onLoadDone.call(box);
		}

	};

})(jQuery);

/*
 * EXPLICIT BOX CONTROLS ----------------------- This is a custom plugin to use
 * with the component BOX. It allows you to activate a box inserted in the DOM
 * after the app.js was loaded, toggle and remove box.
 * 
 * @type plugin @usage $("#box-widget").activateBox(); @usage
 * $("#box-widget").toggleBox(); @usage $("#box-widget").removeBox();
 */
(function($) {

	'use strict';

	$.fn.activateBox = function() {
		$.SystemApp.boxWidget.activate(this);
	};

	$.fn.toggleBox = function() {
		var button = $($.SystemApp.boxWidget.selectors.collapse, this);
		$.SystemApp.boxWidget.collapse(button);
	};

	$.fn.removeBox = function() {
		var button = $($.SystemApp.boxWidget.selectors.remove, this);
		$.SystemApp.boxWidget.remove(button);
	};

})(jQuery);