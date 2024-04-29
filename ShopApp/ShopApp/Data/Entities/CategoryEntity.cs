using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;
using ShopApp.Data.Entities.Identity;

namespace ShopApp.Data.Entities
{
    [Table("tblCategories")]
    public class CategoryEntity
    {
        [Key]
        public int Id { get; set; }

        [Required, StringLength(255)]
        public required string Name { get; set; }

        [StringLength(4000)]
        public string? Description { get; set; }

        [StringLength(255)]
        public string? Image { get; set; }

        [ForeignKey("User")]
        public long? UserId { get; set; }

        public virtual UserEntity? User { get; set; }

    }
}
