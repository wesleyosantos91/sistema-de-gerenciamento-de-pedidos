package main

import (
	"log"
	"modulo-go/pkg/api/router"
	"modulo-go/pkg/config"
	"modulo-go/pkg/db"
	"os"

	"github.com/gin-gonic/gin"
)

func main() {

	env := os.Getenv("APP_ENV")
	if env == "" {
		env = "dev"
	}

	config.LoadAppEnv(env)

	db.Connect()
	db.RunMigrations(db.InstanceSqlDatabase())

	r := gin.Default()

	router.SetupRoutes(r, db.DB)

	serverPort := os.Getenv("SERVER_PORT")
	if serverPort == "" {
		serverPort = "8080"
	}

	log.Println("Server running on port", serverPort)

	r.Run(":" + serverPort)
}
