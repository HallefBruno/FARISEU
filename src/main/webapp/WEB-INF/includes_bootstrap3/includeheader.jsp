<%-- 
    Document   : includeheader
    Created on : 25/11/2018, 17:30:31
    Author     : Brno
--%>

<%@page import="java.util.LinkedList"%>
<%@page import="com.fariseu.negocio.NTela"%>
<%@page import="com.fariseu.entidade.ETela"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="com.fariseu.negocio.NMenu"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.Connection"%>
<head>
    
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <style>
	.label,.glyphicon { margin-right:2px; }
        
        body {
            background-color: #ededed;
        }
        
	#bcolorUrl:hover {
	    background-color: #7254ad;
	    color: white;
	}
	
	/*!
	* IE10 viewport hack for Surface/desktop Windows 8 bug
	* Copyright 2014-2015 Twitter, Inc.
	* Licensed under MIT (https://github.com/twbs/bootstrap/blob/master/LICENSE)
	*/

	/*
	 * See the Getting Started docs for more information:
	 * http://getbootstrap.com/getting-started/#support-ie10-width
	 */
	@-ms-viewport     { width: device-width; }
	@-o-viewport      { width: device-width; }
	@viewport         { width: device-width; }
	
	
    </style>
    <script>
	// NOTICE!! DO NOT USE ANY OF THIS JAVASCRIPT
	// IT'S JUST JUNK FOR OUR DOCS!
	// ++++++++++++++++++++++++++++++++++++++++++
	/*!
	 * Copyright 2014-2015 Twitter, Inc.
	 *
	 * Licensed under the Creative Commons Attribution 3.0 Unported License. For
	 * details, see https://creativecommons.org/licenses/by/3.0/.
	 */
	// Intended to prevent false-positive bug reports about Bootstrap not working properly in old versions of IE due to folks testing using IE's unreliable emulation modes.
	(function () {
	  'use strict';

	    function emulatedIEMajorVersion() {
		var groups = /MSIE ([0-9.]+)/.exec(window.navigator.userAgent);
		if (groups === null) {
		  return null;
		}
		var ieVersionNum = parseInt(groups[1], 10);
		var ieMajorVersion = Math.floor(ieVersionNum);
		return ieMajorVersion;
	    }

	    function actualNonEmulatedIEMajorVersion() {
	      // Detects the actual version of IE in use, even if it's in an older-IE emulation mode.
	      // IE JavaScript conditional compilation docs: https://msdn.microsoft.com/library/121hztk3%28v=vs.94%29.aspx
	      // @cc_on docs: https://msdn.microsoft.com/library/8ka90k2e%28v=vs.94%29.aspx
		var jscriptVersion = new Function('/*@cc_on return @_jscript_version; @*/')() // jshint ignore:line
		if (jscriptVersion === undefined) {
		    return 11; // IE11+ not in emulation mode
		}
		if (jscriptVersion < 9) {
		    return 8; // IE8 (or lower; haven't tested on IE<8)
		}
		return jscriptVersion; // IE9 or IE10 in any mode, or IE11 in non-IE11 mode
	    }

	    var ua = window.navigator.userAgent;
	    if (ua.indexOf('Opera') > - 1 || ua.indexOf('Presto') > - 1) {
		return; // Opera, which might pretend to be IE
	    }
	    var emulated = emulatedIEMajorVersion();
	    if (emulated === null) {
		return; // Not IE
	    }
	    var nonEmulated = actualNonEmulatedIEMajorVersion();

	    if (emulated !== nonEmulated) {
		window.alert('WARNING: You appear to be using IE' + nonEmulated + ' in IE' + emulated + ' emulation mode.\nIE emulation modes can behave significantly differently from ACTUAL older versions of IE.\nPLEASE DON\'T FILE BOOTSTRAP BUGS based on testing in IE emulation modes!');
	    }
	})
	
	(function () {
	    'use strict';

	    if (navigator.userAgent.match(/IEMobile\/10\.0/)) {
	      var msViewportStyle = document.createElement('style');
	      msViewportStyle.appendChild(
		document.createTextNode(
		  '@-ms-viewport{width:auto!important}'
		)
	      )
	      document.querySelector('head').appendChild(msViewportStyle);
	    }

	})
	
    </script>
    
    <script>
	
    </script>
    
</head>

    <nav class="navbar navbar-default navbar-fixed-top" role="navigation">

        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
	    <a class="navbar-brand" href="#">Fariseu</a>
        </div>
	
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li class="active"><a href="/Fariseu/PaginaIndex.jsp"><span class="glyphicon glyphicon-home"></span>Dashboard</a></li>
                <li><a href="#"><span class="glyphicon glyphicon-calendar"></span>Calendar</a></li>
		
		
		    <%

			if (!request.getRequestURI().equals("")) {///Fariseu/PaginaIndex.jsp
			    Connection oConn = (Connection) request.getAttribute("conexao");
			    List<LinkedHashMap<String, Object>> menu = new NMenu(oConn).getMenu();
			    
			    for (LinkedHashMap menus : menu) {
				
		    %>
				<li class="dropdown">
				    
				    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><!--<span class="glyphicon glyphicon-list-alt"></span>Widgets <b class="caret"></b>-->
					<% out.println(menus.get("descricao"));%><b class="caret"></b>
				    </a> 

				    <ul class="dropdown-menu">
					<%
					    List<LinkedHashMap<String,Object>> tela = new NTela(oConn).getTela(Integer.valueOf(menus.get("id").toString()));
					    for(LinkedHashMap<String,Object> telas : tela) {
					%>
						<li>
						    <a id="bcolorUrl" href=<%out.println(telas.get("url"));%>><%out.println(telas.get("descricao"));%></a>
						    <li class="divider"></li>
						</li>
						
					<%
					    }
					%>
					
				    </ul>
				    
				</li>
		    <%
			    }
			}

		    %>
		
		
                <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="glyphicon glyphicon-search"></span>Search <b class="caret"></b></a>
                    <ul class="dropdown-menu" style="min-width: 300px;">
                        <li>
                            <div class="row">
                                <div class="col-md-12">
                                    <form class="navbar-form navbar-left" role="search">
					<div class="input-group">
					    <input type="text" class="form-control" placeholder="Search" />
					    <span class="input-group-btn">
						<button class="btn btn-primary" type="button">Go!</button>
					    </span>
					</div>
                                    </form>
                                </div>
                            </div>
                        </li>
                    </ul>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
		    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
			<span class="glyphicon glyphicon-comment"></span>Chats <span class="label label-primary">42</span>
		    </a>
                    <ul class="dropdown-menu">
                        <li><a href="#"><span class="label label-warning">7:00 AM</span>Hi :)</a></li>
                        <li><a href="#"><span class="label label-warning">8:00 AM</span>How are you?</a></li>
                        <li><a href="#"><span class="label label-warning">9:00 AM</span>What are you doing?</a></li>
                        <li class="divider"></li>
                        <li><a href="#" class="text-center">View All</a></li>
                    </ul>
                </li>
                <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown"><span
                    class="glyphicon glyphicon-envelope"></span>Inbox <span class="label label-info">32</span>
                </a>
                    <ul class="dropdown-menu">
                        <li><a href="#"><span class="label label-warning">4:00 AM</span>Favourites Snippet</a></li>
                        <li><a href="#"><span class="label label-warning">4:30 AM</span>Email marketing</a></li>
                        <li><a href="#"><span class="label label-warning">5:00 AM</span>Subscriber focused email
                            design</a></li>
                        <li class="divider"></li>
                        <li><a href="#" class="text-center">View All</a></li>
                    </ul>
                </li>
                <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown"><span
                    class="glyphicon glyphicon-user"></span>Admin <b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li><a href="#"><span class="glyphicon glyphicon-user"></span>Profile</a></li>
                        <li><a href="#"><span class="glyphicon glyphicon-cog"></span>Settings</a></li>
                        <li class="divider"></li>
                        <li><a href="#"><span class="glyphicon glyphicon-off"></span>Logout</a></li>
                    </ul>
                </li>
            </ul>
        </div>

    </nav>

