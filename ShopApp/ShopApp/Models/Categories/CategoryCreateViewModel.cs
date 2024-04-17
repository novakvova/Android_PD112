namespace ShopApp.Models.Categories
{
    public class CategoryCreateViewModel
    {
        public required string Name { get; set; }
        public string? Description { get; set; }
        public IFormFile? Image { get; set; }
    }
}
