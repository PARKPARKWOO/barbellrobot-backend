package com.example.core.managemnet.adapter.out

import com.example.core.common.error.ErrorCode
import com.example.core.common.error.ServiceException
import com.example.core.managemnet.adapter.out.persistence.entity.ManagementEntity
import com.example.core.managemnet.adapter.out.persistence.repository.ManagementRepository
import com.example.core.managemnet.application.port.command.AddManagementMemberCommand
import com.example.core.managemnet.application.port.out.ManagementJpaPort
import com.example.core.user.member.adapter.out.persistence.repository.MemberRepository
import com.example.core.user.member.application.out.MemberJpaPort
import com.example.core.user.trainer.adapter.out.persistence.repository.TrainerRepository
import com.example.domain.user.MemberSummary
import com.example.domain.user.Trainer
import org.springframework.stereotype.Component
import java.util.*

@Component
class ManagementJpaAdapter(
    private val managementRepository: ManagementRepository,
    private val trainerRepository: TrainerRepository,
    private val memberRepository: MemberRepository,
) : ManagementJpaPort {
    override fun create(trainer: Trainer) {
        val trainerEntity = trainerRepository.findById(trainer.id).orElseThrow {
            throw ServiceException(ErrorCode.TRAINER_NOT_FOUND)
        }
        val managementEntity = ManagementEntity(
            trainer = trainerEntity,
        )
        managementRepository.save(managementEntity)
    }

    override fun addManagementMember(command: AddManagementMemberCommand) {
        val member = memberRepository.findById(command.memberId).orElseThrow {
            throw ServiceException(ErrorCode.MEMBER_NOT_FOUND)
        }
        managementRepository.findFromTrainerId(command.trainerId)?.addManagementMember(member)
    }

    override fun getManagementMember(trainerId: UUID): List<MemberSummary>? {
        return managementRepository.findFromTrainerId(trainerId)?.let {
            it.memberList.map { member -> member.toDomain().summary() }
        }
    }
}
