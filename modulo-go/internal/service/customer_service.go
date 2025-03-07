package service

import (
	"errors"
	"modulo-go/pkg/model"
	"modulo-go/pkg/repository"
)

type CustomerService struct {
	repo *repository.CustomerRepository
}

func NewCustomerService(repo *repository.CustomerRepository) *CustomerService {
	return &CustomerService{repo: repo}
}

func (s *CustomerService) GetAllCustomers(offset, limit int) ([]model.Customer, int64, error) {
	return s.repo.FindAll(offset, limit)
}

func (s *CustomerService) GetCustomerByID(id string) (model.Customer, error) {
	customer, err := s.repo.FindByID(id)
	if err != nil {
		return customer, errors.New("customer not found")
	}
	return customer, nil
}

func (s *CustomerService) CreateCustomer(customer *model.Customer) error {
	return s.repo.Create(customer)
}

func (s *CustomerService) UpdateCustomer(id string, data map[string]interface{}) error {
	return s.repo.Update(id, data)
}

func (s *CustomerService) DeleteCustomer(id string) error {
	return s.repo.Delete(id)
}
