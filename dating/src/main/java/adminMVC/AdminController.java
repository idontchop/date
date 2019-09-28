package adminMVC;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * This class and the others in AdminMVC is meant to only be a basic interface
 * during development.
 * 
 * @author Nathaniel J Dunn <idontchop.com>
 *
 */
@Controller
public class AdminController {
	
	
	/**
	 *  Display Admin menu
	 */
	
	@RequestMapping ( value = "/admin" )
	public String adminMenu () {
		return "admin-menu";
	}

}
