package main

import (
	"modulo-go/internal/db"
	"modulo-go/internal/util"
	"os"
)

func main() {

	env := os.Getenv("APP_ENV")
	if env == "" {
		env = "dev"
	}

	util.LoadAppEnv(env)

	database := db.LoadDatabase()
	defer database.Close()

	db.RunMigrations(database)
}
