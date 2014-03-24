vB_XHTML_Ready.subscribe(init_vbcomment_popupmenus);

function init_vbcomment_popupmenus(baseNode)
{
	if (!YAHOO.lang.isUndefined(YAHOO.vBulletin.vBPopupMenu))
	{
		var menus = YAHOO.util.Dom.getElementsByClassName("vbcommentpopupmenu", undefined, baseNode);
		for (var i = 0; i < menus.length; i++)
		{
			var menuobj = new vB_vbcommentPopupMenu(menus[i], YAHOO.vBulletin.vBPopupMenu);
			YAHOO.vBulletin.vBPopupMenu.register_menuobj(menuobj);
		}
	}
	else
	{
		console.log('Popup menu init in wrong order -- vbcomment popup init');
	}
}


function vB_vbcommentPopupMenu(container, factory)
{
	this.init(container, factory);
	if (container.attributes && container.attributes.rel && container.attributes.rel.value)
	{
		this.postid = container.attributes.rel.value;
	}
	else if (container.title)
	{
		this.postid = container.title;
	}
	else
	{
		window.status = container.parentNode.title = "No vbcomment set.";
	}
}

vB_vbcommentPopupMenu.prototype = new PopupMenu();

// #############################################################################
// vB_vbcomment_Handler
// #############################################################################


/**
* Submit OnReadyStateChange callback. Uses a closure to keep state.
* Remember to use me instead of "this" inside this function!
*/
vB_vbcommentPopupMenu.prototype.handle_submit = function(ajax)
{
	if (ajax.responseXML)
	{
		
		// check for error first
		var error = ajax.responseXML.getElementsByTagName('error');
		if (error.length)
		{
			alert(error[0].firstChild.nodeValue);
		}
		else
		{
			this.close_menu();
    		var postid = ajax.responseXML.getElementsByTagName("postid")[0];
    		var rep = ajax.responseXML.getElementsByTagName("info")[0];
    		var divid = 'vb_list_comment_' + postid.firstChild.data;
    		var postdivid = 'post_message_' + postid.firstChild.data;
    		var totalid = 'totalvbcm' + postid.firstChild.data;
            var oTable = document.getElementById(divid);
			var oTotal = document.getElementById(totalid);
            if (!oTable)
            {
                document.getElementById(postdivid).innerHTML += '<br class="clear"><div id="vb_list_comment_'+ postid.firstChild.data+'" class="vb_comments">'+ rep.firstChild.data+'<br class="clear"></div>';
            }
			else{
				oTable.innerHTML = rep.firstChild.data + oTable.innerHTML;
				if(oTotal) oTotal.innerHTML = parseFloat(oTotal.innerHTML) + 1;
			}
			//force a reload of the rep menu on the next activation so that
			//the duplicate error will show.
			this.menu.parentNode.removeChild(this.menu);
			this.menu = null;

			//alert(repinfo.firstChild.nodeValue);
		}
	}
}

/**
* Populate OnReadyStateChange callback. Uses a closure to keep state.
* Remember to use me instead of "this" inside this function!
*/
vB_vbcommentPopupMenu.prototype.handle_menu_load  = function(ajax)
{
	if (ajax.responseXML)
	{
		if (!this.menu)
		{
			// Create new div to hold vbcomment menu html
			this.menu = document.createElement('div');
			this.menu.id = this.divname;

			YAHOO.util.Dom.addClass(this.menu, "popupbody");
			YAHOO.util.Dom.addClass(this.menu, "popuphover");

			this.container.appendChild(this.menu);
			YAHOO.util.Event.on(this.menu, "keypress", this.repinput_onkeypress, this, true);
		}

		// check for error first
		var error = ajax.responseXML.getElementsByTagName('error');
		if (error.length)
		{
			this.menu.innerHTML = '<div class="blockbody"><div class="blockrow">' + error[0].firstChild.nodeValue + '</div></div>';
		}
		else
		{
			this.menu.innerHTML = ajax.responseXML.getElementsByTagName('vbcommentbit')[0].firstChild.nodeValue;

			var inputs = fetch_tags(this.menu, 'input');
			for (var i = 0; i < inputs.length; i++)
			{
				if (inputs[i].type == 'submit')
				{
					var sbutton = inputs[i];
					var button = document.createElement('input');
					button.type = 'button';
					button.className = sbutton.className;
					button.value = sbutton.value;
					YAHOO.util.Event.addListener(button, 'click', vB_vbcommentPopupMenu.prototype.submit_onclick, this, true);
					sbutton.parentNode.insertBefore(button, sbutton);
					sbutton.parentNode.removeChild(sbutton);
					button.name = sbutton.name;
					button.id = sbutton.name + '_' + this.postid
				}
			}

			this.activate_menu();
			this.open_menu(ajax.argument.e);
		}
	}
}

/**
* Handles click events on vbcomment submit button
*/

vB_vbcommentPopupMenu.prototype.submit_onclick = function (e)
{
	this.submit();
	YAHOO.util.Event.preventDefault(e);
	return false;
}

/**
*	Catches the keypress of the vbcomment controls to keep them from submitting to inlineMod
*/
vB_vbcommentPopupMenu.prototype.repinput_onkeypress = function (e)
{
	switch (e.keyCode)
	{
		case 13:
		{
			YAHOO.util.Event.stopEvent(e);
			this.submit_onclick(e);
			return false;
		}
		default:
		{
			return true;
		}
	}
}

/**
* Queries for proper response to vbcomment, response varies
*
*/

vB_vbcommentPopupMenu.prototype.load_menu = function(e)
{
	// IE loses the event in the argument call below so we send a copy
	var eventCopy = {};
	for (var i in e)
	{
		eventCopy[i] = e[i];
	}
	YAHOO.util.Connect.asyncRequest("POST", "comment.php?do=comment&p=" + this.postid, {
		success: this.handle_menu_load,
		failure: this.handle_ajax_error,
		timeout: vB_Default_Timeout,
		scope: this,
		argument: {e:eventCopy}
	}, SESSIONURL + "securitytoken=" + SECURITYTOKEN + "&p=" + this.postid + "&ajax=1");
}

/**
* Handles AJAX Errors
*
* @param	object	YUI AJAX
*/
vB_vbcommentPopupMenu.prototype.handle_ajax_error = function(ajax)
{
	//TODO: Something bad happened, try again
	vBulletin_AJAX_Error_Handler(ajax);
};

/**
* Submits vbcomment
*
*/
vB_vbcommentPopupMenu.prototype.submit = function()
{
	this.psuedoform = new vB_Hidden_Form('comment.php');
	this.psuedoform.add_variable('ajax', 1);
	this.psuedoform.add_variables_from_object(this.menu);

	YAHOO.util.Connect.asyncRequest("POST", "comment.php?do=insert&p=" + this.psuedoform.fetch_variable('p'), {
		success: this.handle_submit,
		failure: this.handle_ajax_error,
		timeout: vB_Default_Timeout,
		scope: this
	}, SESSIONURL + "securitytoken=" + SECURITYTOKEN + "&" + this.psuedoform.build_query_string());
}
function viewallvbcm(postid)
{
	postidsave = postid;
	fetch_object("vbcmloading").style.display = "";
	vbcmAjax = new vB_AJAX_Handler(true);
	vbcmAjax.onreadystatechange(vbcmResponse);
	vbcmAjax.send('comment.php', 'do=listcm&p='+postid);
}
function vbcmResponse()
{
	if (vbcmAjax.handler.readyState == 4 && vbcmAjax.handler.status == 200)
	{
		fetch_object("vbcmloading").style.display = "none";
		fetch_object("viewallvbcm"+postidsave).style.display = "none";
		fetch_object("vb_list_comment_"+postidsave).innerHTML = vbcmAjax.handler.responseText + '<br class="clear">';
	}
}
/*======================================================================*\
|| ####################################################################
|| # Downloaded: 23:14, Sun Mar 28th 2010
|| # CVS: $RCSfile$ - $Revision: 26385 $
|| ####################################################################
\*======================================================================*/

