package db

import (
	"database/sql"
	"fmt"
	"log"
	"os"

	_ "github.com/lib/pq"
)

func LoadDatabase() *sql.DB {

	dbUser := os.Getenv("DB_USER")
	dbPass := os.Getenv("DB_PASS")
	dbName := os.Getenv("DB_NAME")
	dbHost := os.Getenv("DB_HOST")
	dbPort := os.Getenv("DB_PORT")

	if dbUser == "" || dbPass == "" || dbName == "" || dbHost == "" || dbPort == "" {
		log.Fatal("❌ Erro está faltando variáveis de ambiente para a conexão com o banco")
	}

	dbURL := fmt.Sprintf("postgres://%s:%s@%s:%s/%s?sslmode=disable", dbUser, dbPass, dbHost, dbPort, dbName)

	db, err := sql.Open("postgres", dbURL)
	if err != nil {
		log.Fatalf("❌ Erro ao conectar no banco: %v", err)
	}

	if err := db.Ping(); err != nil {
		log.Fatalf("❌ Erro ao testar conexão com o banco: %v", err)
	}

	fmt.Println("✅ Conexão com banco estabelecida!")
	return db
}
