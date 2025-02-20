package db

import (
	"database/sql"
	"log"

	"github.com/golang-migrate/migrate/v4"
	"github.com/golang-migrate/migrate/v4/database/postgres"
	_ "github.com/golang-migrate/migrate/v4/source/file"
)

func RunMigrations(db *sql.DB) {

	driver, err := postgres.WithInstance(db, &postgres.Config{})

	if err != nil {
		log.Fatalf("❌ Erro ao criar driver do migrate: %v", err)
	}

	m, err := migrate.NewWithDatabaseInstance(
		"file://internal/db/migrations/",
		"postgres", driver,
	)
	if err != nil {
		log.Fatalf("❌ Erro ao criar migrate instance: %v", err)
	}

	err = m.Up()
	if err != nil && err != migrate.ErrNoChange {
		log.Fatalf("❌ Erro ao aplicar migrations: %v", err)
	}

	log.Println("✅ Migrations aplicadas com sucesso!")
}
