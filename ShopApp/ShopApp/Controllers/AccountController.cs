using AutoMapper;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using ShopApp.Constants;
using ShopApp.Data.Entities.Identity;
using ShopApp.Interfaces;
using ShopApp.Models.Account;

namespace ShopApp.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class AccountController : ControllerBase
    {
        private readonly UserManager<UserEntity> _userManager;
        private readonly IJwtTokenService _jwtTokenService;
        private readonly IMapper _mapper;

        public AccountController(UserManager<UserEntity> userManager, IJwtTokenService jwtTokenService,
            IMapper mapper)
        {
            _userManager = userManager;
            _jwtTokenService = jwtTokenService;
            _mapper = mapper; 
        }


        [HttpPost("login")]
        public async Task<IActionResult> Login([FromBody] LoginViewModel model)
        {
            var user = await _userManager.FindByEmailAsync(email: model.Email);
            if (user == null)
            {
                return BadRequest();
            }
            var isAuth = await _userManager.CheckPasswordAsync(user, model.Password);
            if (!isAuth)
            {
                return BadRequest();
            }
            var token = await _jwtTokenService.CreateToken(user);
            return Ok(new { token });
        }

        [HttpPost("register")]
        public async Task<IActionResult> Register([FromBody] RegisterViewModel model)
        {
            var user = await _userManager.FindByEmailAsync(email: model.Email);
            if (user != null)
            {
                return BadRequest();
            }
            user = _mapper.Map<UserEntity>(model);
            var result = _userManager.CreateAsync(user, model.Password)
                       .Result;
            if (result.Succeeded)
            {
                result = _userManager
                    .AddToRoleAsync(user, Roles.User)
                    .Result;
            }
            var token = await _jwtTokenService.CreateToken(user);
            return Ok(new { token });
        }
    }
}
