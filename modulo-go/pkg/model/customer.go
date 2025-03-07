package model

import (
	"time"

	"github.com/google/uuid"
	"gorm.io/gorm"
)

type Customer struct {
	ID        uuid.UUID  `gorm:"type:uuid;primary_key;default:gen_random_uuid()" json:"id"`
	Name      string     `gorm:"type:varchar(255);not null" json:"name"`
	Email     string     `gorm:"type:varchar(255);unique;not null" json:"email"`
	CreatedAt time.Time  `gorm:"autoCreateTime" json:"created_at"`
	UpdatedAt *time.Time `gorm:"default:NULL" json:"updated_at"`
}

func (Customer) TableName() string {
	return "tb_customers"
}

func (c *Customer) BeforeCreate(tx *gorm.DB) (err error) {
	c.ID = uuid.New()
	return
}
