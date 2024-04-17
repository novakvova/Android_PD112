using Microsoft.AspNetCore.Mvc;
using ShopApp.Data.Entities;
using ShopApp.Data;
using ShopApp.Models.Categories;
using SixLabors.ImageSharp.Formats.Webp;
using SixLabors.ImageSharp;
using SixLabors.ImageSharp.Processing;

namespace ShopApp.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class CategoriesController : ControllerBase
    {
        private readonly MyAppContext _appContext;

        public CategoriesController(MyAppContext appContext)
        {
            _appContext = appContext;
        }

        [HttpGet]
        public IActionResult Get()
        {
            var list = _appContext.Categories.ToList();
            return Ok(list);
        }
        [HttpPost]
        public async Task<IActionResult> Create([FromForm] CategoryCreateViewModel model)
        {
            var category = new CategoryEntity
            {
                Name = model.Name,
                Description = model.Description
            };

            if (model.Image != null)
            {
                using MemoryStream ms = new MemoryStream();
                await model.Image.CopyToAsync(ms);

                using Image image = Image.Load(ms.ToArray());

                image.Mutate(x =>
                {
                    x.Resize(new ResizeOptions
                    {
                        Size = new Size(1200),
                        Mode = ResizeMode.Max
                    });
                });
                string imageName = Path.GetRandomFileName() + ".webp";
                string dirSaveImage = Path.Combine(Directory.GetCurrentDirectory(), "images", imageName);

                using var stream = System.IO.File.Create(dirSaveImage);
                await image.SaveAsync(stream, new WebpEncoder());
                category.Image = imageName;
            }

            _appContext.Categories.Add(category);
            _appContext.SaveChanges();
            return Ok(category);
        }

        [HttpPut]
        public IActionResult Edit([FromBody] CategoryEditViewModel model)
        {
            var category = _appContext.Categories.SingleOrDefault(x => x.Id == model.Id);
            if (category == null)
            {
                return NotFound();
            }
            category.Description = model.Description;
            category.Name = model.Name;
            _appContext.SaveChanges();
            return Ok(category);
        }

        [HttpDelete("{id}")]
        public IActionResult Delete(int id)
        {
            var category = _appContext.Categories.SingleOrDefault(x => x.Id == id);
            if (category == null)
            {
                return NotFound();
            }
            _appContext.Categories.Remove(category);
            _appContext.SaveChanges();
            return Ok();
        }
    }
}
