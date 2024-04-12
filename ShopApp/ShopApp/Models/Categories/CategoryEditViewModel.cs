namespace ShopApp.Models.Categories
{
    public class CategoryEditViewModel
    {
        public required int Id { get; set; }
        public required string Name { get; set; }
        public string? Description { get; set; }
    }
}
