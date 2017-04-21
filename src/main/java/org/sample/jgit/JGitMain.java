package org.sample.jgit;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.StringArrayOptionHandler;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.Status;

/**
 * 
 *
 */

public class JGitMain 
{
    private static final String REMOTE_URL = "https://github.com/takehiroman/samplejgit.git";

    @Option(name= "-init",  usage= "print git init")
    private boolean initFlag;

    @Option(name= "-add",  usage= "print git add")
    private boolean addFlag;

    @Option(name= "-commit",  usage= "print git commit")
    private boolean commitFlag;

    @Argument(metaVar = "COMMENT")
    private String comment;

    @Option(name= "-status",  usage= "print git status")
    private boolean statusFlag;

    @Option(name= "-clone",  usage= "print git clone")
    private boolean cloneFlag;

    public static void main(String[] args)throws IOException, GitAPIException {
        JGitMain jgit = new JGitMain();

        CmdLineParser parser = new CmdLineParser(jgit);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            e.printStackTrace();
            return;
        }

        Repository repository;

        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        repository = builder.setGitDir(new File("./myrepo/.git")).build();
        Git git = new Git(repository);

        if(jgit.initFlag){
            		Git.init()
		               .setDirectory(new File("myrepo")) /*myrepoというディレクトリ作成*/
		               .call();
        }

        if(jgit.addFlag){
                 git.add()
                    .addFilepattern(".")
                    .call();

        }

        if(jgit.commitFlag){
                git.commit()
                   .setMessage(jgit.comment)
                   .call();
        }

        if(jgit.statusFlag){
            Status status = git
                            .status()
                            .call();
                System.out.println("Added: " + status.getAdded());
                System.out.println("Changed: " + status.getChanged());
                System.out.println("Conflicting: " + status.getConflicting());
                System.out.println("ConflictingStageState: " + status.getConflictingStageState());
                System.out.println("IgnoredNotInIndex: " + status.getIgnoredNotInIndex());
                System.out.println("Missing: " + status.getMissing());
                System.out.println("Modified: " + status.getModified());
                System.out.println("Removed: " + status.getRemoved());
                System.out.println("Untracked  files: " + status.getUntracked());
                System.out.println("UntrackedFolders: " + status.getUntrackedFolders());

        }

        if(jgit.cloneFlag){
                 Git result = Git.cloneRepository()
                                 .setURI(REMOTE_URL)
                                 .setDirectory(new File("samplejgit"))
                                 .call();
        }
    }
}
