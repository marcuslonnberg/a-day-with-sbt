val superhero = settingKey[String]("Your favourite superhero")
superhero := "Batman"

val writeSuperheroFile = taskKey[Seq[File]]("Writes the superhero to a file")
writeSuperheroFile := {
  val superHeroFile = (resourceManaged in Compile).value / "superhero"
  IO.write(superHeroFile, superhero.value)
  Seq(superHeroFile)
}

resourceGenerators in Compile += writeSuperheroFile.taskValue
