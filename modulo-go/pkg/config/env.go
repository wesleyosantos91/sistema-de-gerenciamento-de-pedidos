package config

import (
	"log"

	"github.com/joho/godotenv"
)

func LoadAppEnv(env string) {
	envFile := ".env." + env

	if err := godotenv.Load(envFile); err != nil {
		log.Fatalf("Erro ao carregar o arquivo %s: %v", envFile, err)
	}
}
