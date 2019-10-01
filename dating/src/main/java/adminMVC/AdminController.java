package adminMVC;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import entities.User;
import repositories.UserRepository;

/**
 * This class and the others in AdminMVC is meant to only be a basic interface
 * during development.
 * 
 * @author Nathaniel J Dunn <idontchop.com>
 *
 */
@Controller
public class AdminController {
	
	@Autowired
	private UserRepository userRepo;
	
	/**
	 *  Display Admin menu
	 */
	
	@RequestMapping ( value = "/admin" )
	public String adminMenu () {
		return "admin-menu";
	}
	
	/**
	 * Display Add User form
	 */
	@RequestMapping ( value = "/admin/create-user", method = RequestMethod.GET)
	public String createUserForm ( ModelMap model ) {
		model.addAttribute("User", new User () );
		return "create-user";
	}

}
