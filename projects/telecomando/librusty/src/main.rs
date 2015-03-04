use openssh::{SessionBuilder, KnownHosts};
use std::path::PathBuf;
use tokio;

pub struct SSHClient {
    address: String,
    port: u16,
    username: String,
    password: Option<String>,
    key_path: Option<PathBuf>,
}

impl SSHClient {
    pub fn new(address: String, port: u16, username: String, password: Option<String>, key_path: Option<PathBuf>) -> Self {
        SSHClient {
            address,
            port,
            username,
            password,
            key_path,
        }
    }

    pub async fn execute(&self, command: &str) -> Result<String, Box<dyn std::error::Error>> {
        let full_address = format!("{}@{}:{}", self.username, self.address, self.port);
        let mut session_builder = SessionBuilder::default();
        session_builder.known_hosts_check(KnownHosts::Add);
        if let Some(key) = &self.key_path {
            session_builder.keyfile(key);
        }

        if let Some(password) = &self.password {
            session_builder.password(password);
        }

        let session = session_builder.connect(full_address).await?;
        let output = session.command(command).output().await?;
        let result = String::from_utf8_lossy(&output.stdout).to_string();

        Ok(result)
    }
}

#[tokio::main]
async fn main() {
    let client = SSHClient::new(
        "127.0.0.1".to_string(),
        22,
        "user".to_string(),
        Some("password".to_string()),
        Some(PathBuf::from("/path/to/key")),
    );

    match client.execute("echo Hello, SSH!").await {
        Ok(output) => println!("Command output: {}", output),
        Err(e) => eprintln!("Error: {}", e),
    }
}

