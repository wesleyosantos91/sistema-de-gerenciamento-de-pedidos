package router

import (
	"modulo-go/internal/service"
	"modulo-go/pkg/api/handler"
	"modulo-go/pkg/repository"

	"github.com/gin-gonic/gin"
	"gorm.io/gorm"
)

func SetupRoutes(r *gin.Engine, db *gorm.DB) {
	customerRepo := repository.NewCustomerRepository(db)
	customerService := service.NewCustomerService(customerRepo)
	customerHandler := handler.NewCustomerHandler(customerService)

	v1 := r.Group("/v1")
	{
		customers := v1.Group("/customers")
		{
			customers.GET("", customerHandler.GetCustomers)
			customers.GET(":id", customerHandler.GetCustomerByID)
			customers.POST("", customerHandler.CreateCustomer)
			customers.PUT(":id", customerHandler.UpdateCustomer)
			customers.DELETE(":id", customerHandler.DeleteCustomer)
		}
	}
}
