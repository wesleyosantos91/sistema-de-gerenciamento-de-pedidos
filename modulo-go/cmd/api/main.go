package main

import (
	"modulo-go/internal/config"
	"modulo-go/internal/db"
	"os"
)

func main() {

	env := os.Getenv("APP_ENV")
	if env == "" {
		env = "dev"
	}

	config.LoadAppEnv(env)

	db.Connect()
	db.RunMigrations(db.InstanceSqlDatabase())
}
