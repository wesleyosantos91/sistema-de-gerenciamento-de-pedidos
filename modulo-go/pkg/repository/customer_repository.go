package repository

import (
	"modulo-go/pkg/model"
	"time"

	"gorm.io/gorm"
)

type CustomerRepository struct {
	db *gorm.DB
}

func NewCustomerRepository(db *gorm.DB) *CustomerRepository {
	return &CustomerRepository{db: db}
}

func (r *CustomerRepository) FindAll(offset, limit int) ([]model.Customer, int64, error) {
	var customers []model.Customer
	var total int64

	result := r.db.Model(&model.Customer{}).Count(&total)
	if result.Error != nil {
		return nil, 0, result.Error
	}

	result = r.db.Offset(offset).Limit(limit).Find(&customers)
	return customers, total, result.Error
}

func (r *CustomerRepository) FindByID(id string) (model.Customer, error) {
	var customer model.Customer
	result := r.db.First(&customer, "id = ?", id)
	return customer, result.Error
}

func (r *CustomerRepository) Create(customer *model.Customer) error {
	return r.db.Create(customer).Error
}

func (r *CustomerRepository) Update(id string, data map[string]interface{}) error {
	currentTime := time.Now()
	data["updated_at"] = &currentTime
	return r.db.Model(&model.Customer{}).Where("id = ?", id).Updates(data).Error
}

func (r *CustomerRepository) Delete(id string) error {
	return r.db.Delete(&model.Customer{}, "id = ?", id).Error
}
